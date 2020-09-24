package com;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
//特别适合用于“分而治之”，递归计算的算法
//不适合执行有block比如有io的任务
public class SumTask extends RecursiveTask<Integer> {

    private Integer start = 0;
    private Integer end = 0;

    public SumTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {

        if (end - start < 100) {
            //小于100时直接返回结果
            int sumResult = 0;
            for (int i = start; i <= end; i++) {
                sumResult += i;
            }
            return sumResult;
        } else {
            //大于一百时进行分割
            int middle = (end + start) / 2;
            SumTask leftSum = new SumTask(this.start, middle);
            SumTask rightSum = new SumTask(middle, this.end);
            leftSum.fork();
            rightSum.fork();
            return leftSum.join() + rightSum.join();
        }
    }

    public static void main(String[] args) {
        SumTask sumTask = new SumTask(1, 999999);
        sumTask.fork();
        System.out.print("result:" + sumTask.join());
        
        ForkJoinPool forkJoinPool=new ForkJoinPool(100);
        SumTask sumTask1 = new SumTask(1, 999999);
        forkJoinPool.submit(sumTask1);
        System.out.print("result1:" + sumTask1.join());
    }
} 