/* ====================================================================================================
 * Project Name     [testJDK]
 * File Name        [sort.Task.java]
 * Creation Date    [2021-10-20]
 *
 * Copyright© 2021 瑞声科技[AAC Technologies Holdings] All Rights Reserved
 *
 * ====================================================================================================
 * Change Log
 * ====================================================================================================
 * 2021-10-20     潘凌云      [Init] .
 * ==================================================================================================== */
package sort;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * <p></p>
 *
 * @author <a href="mailto:panlingyun@aactechnologies.com">潘凌云</a>
 * @version 1.0.0
 * @since jdk 1.8
 */
public class Task extends RecursiveTask<int[]> {

    private int[] list;

    public Task(int[] list){
        this.list = list;
    }

    @Override
    protected int[] compute() {
        if(list.length > 2){
            int[] leftList = new int[list.length/2];
            int[] rightList = new int[list.length/2+ (list.length%2)];
            System.arraycopy(list,0,leftList,0,list.length/2);
            System.arraycopy(list,list.length/2,rightList,0,list.length/2+ (list.length%2));
            Task leftTask = new Task(leftList);
            Task rightTask = new Task(rightList);
            ForkJoinPool forkJoinPool = new ForkJoinPool(2);
            forkJoinPool.invoke(leftTask);
            forkJoinPool.invoke(rightTask);
            return margeInt(leftTask.join(),rightTask.join());
        }else {
            if (list.length == 1){
                return list;
            }else {
                if(list[0] > list[1]){
                    int l = list[0];
                    list[0] = list[1];
                    list[1] = l;
                    return list;
                }
            }
        }
        return new int[0];
    }

    private int[] margeInt(int[] leftList,int[] rightList){
        int[] margeList = new int[leftList.length+rightList.length];
        int i = 0,j=0;
        int k = 0;
        while (i< leftList.length && j< rightList.length){
            if(leftList[i] <= rightList[j]){
                margeList[k++] = leftList[i++];
            }else {
                margeList[k++] = rightList[j++];
            }
        }
        if(i< leftList.length){
            while (i< leftList.length){
                margeList[k++] = leftList[i++];
            }
        }else if(j<rightList.length){
            while (j< rightList.length){
                margeList[k++] = rightList[j++];
            }
        }
        return margeList;
    }

}

