import java.util.Scanner;
import java.util.Random;

public class MMN14 
{
    public static int[] array1; //the array for solution a
    public static int[] array2; //the array for solution b
    public static int n; //the size of the array
    
    public static int count_heap = 0; //count the number of comparisons in solution a
    public static int count_select = 0; //count the number of comparisons in solution a
    
    //args for solution a
    public static int smallest;
    public static int left;
    public static int right;
    
    //args for solution b
    public static int q;
    public static int m;
    public static int randompivot ;
    public static int pivot ;
    
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in); 
        int k = 0; //the number of the smallest numbers that will be presented
        int y_choice; //the user choise between random array to choosen array
        System.out.println("pleas enter n and k <space between>");
        n = scan.nextInt();
        k = scan.nextInt();
        if (k > n)
        {
            k = n;
        }
        array1 = new int[n+1];
        array1[0] = 0;
        array2 = new int[n+1];
        
        System.out.println("pleas choose: \n 1- for random numbers between 0 and 999 , 2 - to give your own numbers");
        y_choice = scan.nextInt();
        if (y_choice == 1)
        {
            for (int i =1; i<=n ; i++)
            {
                array1[i] = (int)(999*Math.random());
            }
        }
        else if (y_choice == 2)
        {
            System.out.println("pleas enter "+ n +" numbers between 0 and 999 <space between>");
            for (int i =1; i<=n ; i++)
            {
                array1[i] = scan.nextInt() ;
            }
        }        
        COPY_ARRAY (array1, array2);
        
        // present the array that was choosen
        System.out.println(" ");
        System.out.println("the array: ");
        for (int i=1; i<=n ; i++)
        {
            System.out.print(array1[i]+" ");
        }
        System.out.println(" ");
        
        //solution a
        BUILD_MIN_HEAP(array1,n);        
        System.out.println(" ");
        System.out.println("the "+ k +" smallest numbers insorted order are:");
        for (int i=0; i< k ; i++)
        {
            System.out.print(HEAP_EXTRACT_MIN(array1,n-i)+" ");
        }
        System.out.println("");
        
        //solution b
        RANDOMIZED_SELECT(array2,1, n, k);
        QUICKSORT(array2,1,k);
        
        System.out.println(" ");
        System.out.println("number of comparisons for algorithm 1 <heap> ="+  count_heap);
        System.out.println("number of comparisons for algorithm 2 <select> = "+ count_select);
    }
    
    /**
     *Copy the values from one array to the other array
     * @param array - the array that will be  
     * @param n -the length of the array.
     * @param i - the index (begin at the middle of the array because the bigger index represent the leafes.
     */
    public static void COPY_ARRAY (int[] array_a , int[] array_b) 
    {
        for (int i=1; i <=n ; i++)
        {
            array_b[i]  = array_a[i];
        }

    }
    
    /**
     *Build a minimum heap by array and return a sorted array
     * @param array - the array that will built the heap  
     * @param n -the length of the array.
     * @param i - the index (begin at the middle of the array because the bigger index represent the leafes.
     */
    public static void BUILD_MIN_HEAP (int[] array , int n) 
    {
        for (int i=n/2; i >= 1 ; i--)
        {
            MIN_HEAPIFY(array, i, n);
        }

    }
    
     /**
     *Organize the heap so that the root of each sub-heap will be the smollest number.
     * @param array - the array that will be  
     * @param n -the length of the array.
     * @param i - the index (begin at the middle of the array because the bigger index represent the leafes.
     * @param left -the left son of the root i.
     * @param right -the right son of the root i.
     */
    public static void MIN_HEAPIFY (int[] array , int j, int n)
    { 
        left = 2*j;
        right = 2*j+1; 
        if (left <= n)  //chek if the root has left son and which number of the two is the smallest
        {
            if (array[left] < array[j])
            {
                smallest = left; //if the left son smallest than the father (i)
            }
            count_heap++;
        }
        else
        {
            smallest = j; //if the root is smallest than his left son
        }     
        if (right <= n) //chek if the right son is the amallest so far
        {
            if (array[right] < array[smallest])
            {
                smallest = right; //if the right son is the smallest than his father and his left brother
            }
            count_heap++;
        }
        if (smallest != j) //that mean that the heap wasn't organized
        {
            EXCHANGE(array, j, smallest);
            MIN_HEAPIFY(array , smallest, n); //organize the heap
        }        
    }
 
     /**
     *Exchange between two arguments in the array.
     * @param array - the array that will be.  
     * @param i - one of the argument to replace.
     * @param j - the second argument to replace.
     * @param temp -temporary parameter.
     */
    public static void EXCHANGE(int[] array, int i, int j)
    {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp; 
    }
    /**
     *Extract the smallest nember of the heap and re-organize the heap.
     * @param array - the array that will be  
     * @param n - the length of the array.
     * @param min - the smallest number in the array.
     * @return the smallest number of the array.
     */   
    public static int HEAP_EXTRACT_MIN(int[] array, int n)
    {
        if (n < 1 )
        {
             System.out.println("heap underflow");
        }
        int min = array[1];
        array[1] = array[n];
        n = n-1;
        MIN_HEAPIFY (array , 1, n);
        return min ; 
    }

    
      /**
     *Find the minimum of the numbers in the array.
     * @param array - the array that will be  
     * @param p -the beginning of the array.
     * @param r - the ending of the array.
     * @param i - the index.
     * @return the i's smallest number of the array.
     */
    public static int RANDOMIZED_SELECT(int[] array, int p, int r, int i)
    {
        if (p == r)
        {
            return array[p];
        }
        q = RANDOMIZED_PARTITION(array,p,r);
        m = q-p+1;
        if (i == m)
        {
            return array[q];
        }
        else if (i < m)
        {
            return RANDOMIZED_SELECT(array,p,q-1,i);
        }
        else
        {
            return RANDOMIZED_SELECT(array,q+1,r,i-m);
        }
     
    }
    
    /**
    *  Doing random divide for the array.
    * @param array - the array that will be random divide.
    * @param p - the beginning of the array.
    * @param r - the ending of the array.
    * @return himself.
    */
    public static int RANDOMIZED_PARTITION(int[] array, int p, int r)
    {
        randompivot = (int)((Math.random() * (r - p)) + p);
        EXCHANGE(array,r,randompivot);
        return PARTITION(array,p,r);
    }
  
    /**
     * Divide the array for the method QUICKSORT.
     * @param array - the array that the PARTITION will divide. 
     * @param p - the beginning of the array.
     * @param r - the ending of the array.
     * @return the beginning array after the array divided.
     */
    public static int PARTITION (int[] array, int p, int r)
    {
        pivot = array[r]; 
        int i = p - 1; 
        for (int j = p; j <= r-1; j++)
        {
            if (array[j] <= pivot)
            {
                i++;
                EXCHANGE(array,i,j);
            }
            count_select++;
        }
        EXCHANGE(array,i+1,r);
        return i+1;
    }
    
    /**
     * QUICKSORT copy the array "array".
     * @param array - the array that will be sorting
     * @param p - beginning of the array.
     * @param r - the ending of the array.
     */
    public static void QUICKSORT(int[] array, int p, int r)
    {
        if(p<r)
        {
            int q = PARTITION(array,p,r);
            QUICKSORT(array,p,q-1);
            QUICKSORT(array,q+1,r);
        }
    }
    
}
