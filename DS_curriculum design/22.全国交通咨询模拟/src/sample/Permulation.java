package sample;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2021-12-31 3:40
 */
//全排列的类
public class Permulation {
    //是否还有其他排列
    public boolean nextPermutation(int[] arr) {
        int postLeft = -1;
        for (int i = arr.length - 1; i > 0; i--) {
            if (arr[i - 1] < arr[i]) {
                postLeft = i - 1;
                break;
            }
        }
        if (postLeft < 0) {
            return false;
        }

        int postRight = -1;
        for (int i = arr.length - 1; i >= postLeft; i--) {
            if (arr[i] > arr[postLeft]) {
                postRight = i;
                break;
            }
        }
        swap(arr, postLeft, postRight);
        reverse(arr, postLeft + 1, arr.length);
        return true;
    }

    //交换数组中ind1位置和ind2位置的元素
    public void swap(int[] arr, int ind1, int ind2) {
        int t = arr[ind1];
        arr[ind1] = arr[ind2];
        arr[ind2] = t;
    }

    //改变数组里面的元素的位置
    public void reverse(int[] arr, int ind1, int ind2) {
        for (int i = 0; i < (ind2 - ind1) / 2; i++) {
            swap(arr, ind1 + i, ind2 - 1 - (i));
        }
    }
}
