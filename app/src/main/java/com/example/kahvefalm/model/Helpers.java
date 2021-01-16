package com.example.kahvefalm.model;

public class Helpers {



    /*Search value in array and
    return index
     */
    public static int BinarySearchStr(String[] array,String value){

        int index = 0;

        for(int i = 0;i < array.length;i++){
            if(array[i].equals(value)){
                index = i;
            }
        }

        return index;

    }


}
