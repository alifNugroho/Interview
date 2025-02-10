package com.alif.project.interview.serviceTest;

import com.alif.project.interview.service.impl.NearestPartnerSearchServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class NearestPartnerSearchServiceTest {

    @InjectMocks
    private NearestPartnerSearchServiceImpl nearestPartnerSearchService;

    @Test
    public void NearestPartnerSearchService_GetResult_ReturnOneOrMoreThanOne() {

        int[] input = new int[]{4, 9, 1, 32, 13, 5};

        List<int[]> getRes = nearestPartnerSearchService.getResult(input);

        Assertions.assertThat(getRes).isNotNull();
        Assertions.assertThat(getRes.getFirst()).isEqualTo(new int[]{4,5});
    }
}
