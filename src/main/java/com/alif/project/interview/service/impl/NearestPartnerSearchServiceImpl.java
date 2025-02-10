package com.alif.project.interview.service.impl;

import com.alif.project.interview.service.NearestPartnerSearchService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class NearestPartnerSearchServiceImpl implements NearestPartnerSearchService {

    @Override
    public List<int[]> getResult(int[] arr) {

        List<int[]> list = new ArrayList<>();
        Arrays.sort(arr);
        int minDiff = Integer.MAX_VALUE;

        for (int i = 1; i < arr.length; i++) {
            int diff = Math.abs(arr[i] - arr[i-1]);
            minDiff = Math.min(minDiff, diff);
        }

        for (int[] ls : twoSum(arr, minDiff)) {
            list.add(ls);
        }
        return list;
    }

    public List<int[]> twoSum(int[] nums, int target) {
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (Math.abs(nums[i] - nums[j]) == target) {
                    list.add(new int[] {nums[i], nums[j]});

                }
            }
        }
        return list;
    }
}
