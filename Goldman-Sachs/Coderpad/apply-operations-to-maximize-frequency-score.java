/* 
1. if I choose a number which is already present in nums for the becoming the most frequent, I save on k. I can use those  savings to make other numbers equal to k.
2. Let' say I choose a number greater than maxValue as K, i.e. maxValue+1, then I have to make even the max value equal to k. And not only that, I would need k to make all elements which could become equal to maxValue, also to add 1 to make them as maxValue+1.
3. So always choosing a number exisitig already is a better choice.
*/
