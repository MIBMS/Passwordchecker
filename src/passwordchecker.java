// PASSWORD CHECKER - ASSESSED COURSEWORK - ALGORITHMS IN JAVA - LT 2015
// NAME: MOHAMED ILHAM BIN MOHAMED SALLEH / CLASS GROUP 2

import java.io.BufferedReader;
import java.io.FileReader;

class PasswordChecker
{
         
  public static void main(String[] args)
  {

    // Our program expects two inputs, first the path to the file
    // containing the taboo words and then the password. It assumes 
    // that the list is lowercases only and also turns the password 
    // into lowercases.

    String path;
    String pwd;
 
    if(args.length != 2)
    {
      System.out.println("Usage: java PasswordChecker <path to file> <password>");
      return;
    }
    else
    {
      path = args[0];
      pwd = args[1].toLowerCase();
    }

    // Let's start by creating a large enough sorted array and feeding 
    // in the taboo words from the file. The method readFromFile is 
    // provided, but you will need to code up the add method that it 
    // uses as well as the getSize method used below.

    SortedArray a = new SortedArray(300000);
    System.out.print("Reading words from file " + path + "... ");
    a.readFromFile(path);
    System.out.println("done!");
    System.out.println("File contains " + a.getSize() + " words.");


    // Now let's actually sort the sorted array. For this you will need
    // to implement a sorting algorithm that works on arrays of strings
    // rather than on integers. Currently sort does nothing.
    
    System.out.print("Sorting ... ");
    a.sort();
    System.out.println(" done!");  

    // Use the following line to print the content of the sorted array
    // after the sorting to check whether your sorting algorithm works
    // correctly. In case you are having troubles you may find it useful
    // to also add prints in other places for debugging purposes. That's 
    // totally fine, but please remove them from the final program that
    // you hand in.

    //a.print();

    // Your are also asked to implement two different algorithms for 
    // finding a string in our data structure SortedArray. Let's see 
    // if they work properly.

    String testString = "cat";
    System.out.println("Searching for \'" + testString + "\'...");
    if(a.findLinearSearch(testString)) 
      System.out.println("Found with linear search.");
    else 
      System.out.println("Linear search failed.");
    if(a.findBinarySearch(testString)) 
      System.out.println("Found with binary search.");
    else
      System.out.println("Binary search failed.");
 
    // Last but not least, let's check whether the password that you typed in
    // is any good. For this you will have to complete the method checkPassword 
    // that you find below.  
    
    System.out.println("Checking if password \'" + pwd + "\' is good ...");
    System.out.println(checkPassword(a,pwd));
    

    

  }

  // Method checkPassword gets a SortedArray and a String and runs some
  // sanity checks on the password. It returns a String message that 
  // reflects whether the program deems the password and good. See the
  // description of the assessed coursework for what common mistakes you
  // should check for.

  public static String checkPassword(SortedArray a, String pwd)
  {      
    // Creates three booleans which check each of the conditions and flag to the final
	 //if statement the tests that the password has failed
	  String message;
	  String message1 = "The password is too short.";
	  String message2 = "The password is a common word.";
	  String message3 = "The password is made up of 2 common words.";
	  boolean safe1 = true;
	  boolean safe2 = true;
	  boolean safe3 = true;
	  if (pwd.length() <= 7)
		  safe1 = false; 
	  if (a.findBinarySearch(pwd) == true)
		  safe2 = false;
	  for (int i = 0; i < pwd.length(); i++)
	  { //third test, breaks the password into two parts of every length and checks
		  //whether both parts are common words. If so, then the password is flagged as unsafe.
		  String substring1 = pwd.substring(0, i+1);
		  String substring2 = pwd.substring(i+1, pwd.length());
		  if (a.findBinarySearch(substring1)==true && a.findBinarySearch(substring2)==true)
		  {
			  safe3 = false;
			  break;
		  }
	  } 
	  if (safe1 == false || safe2 == false || safe3 == false)
	  {
		  message = "This password is unsafe because:";
		  if (safe1 == false)
			  message = message + "\n " + message1;
		  if (safe2 == false)
			  message = message + "\n " + message2;
		  if (safe3 == false)
			  message = message + "\n " + message3;
	  }
	  else
		  message = "This password is 'safe'";
	  
	  
	  return message;
  }

}

class SortedArray {

  // The data that we save in a SortedArray is an array of strings arr, 
  // an integer max denoting the maximum number of elements that can 
  // be stored in it, and an integer size that corresponds to the number 
  // of elements currently stored in arr.

  String[] arr;
  int max;
  int size;

  // The constructor "SortedArray" takes one argument max and creates an array
  // arr of length max and sets size to zero

  public SortedArray(int max)
  {
	  //initializes String[] arr, int max, and int size 
	  //so that the string array arr has size
	  //max, max stores the value max, and size is zero.
	  this.arr = new String[max];
	  this.max = max;
	  this.size = 0;	  
  }

  // Method "readFromFile" takes path name of file containing list of taboo 
  // words and adds them to array arr. For example "test.txt" may be 
  // <file test.txt starts here>
  // lion
  // cat
  // dog
  // <file test.txt ends here>
  // Then a.readFromFile("test.txt") will create array arr=[lion,cat,dog].

  public void readFromFile(String pathToFile)
  {
    try(BufferedReader br = new BufferedReader(new FileReader(pathToFile))) 
    {
      String line = br.readLine();
      while (line != null) 
      { 
	// This tries to add the string line to arr. For this to work you 
        // will have to complete method add below.
        add(line);
        line = br.readLine();
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public void add(String str)
  {
    // takes a String str and adds it at the end of the array arr
	// and updates the size
	  arr[size] = str;
	  size++;
  }

  // Method "sort" sorts the array arr using any of the methods that we have 
  // seen in the lecture and/or classes, you can also use other sorting 
  // algorithms if you prefer to do so. Since the input will be large it is 
  // advisable to use an algorithm with whose running time is bounded by 
  // O(n log n), where n is the size of arr. Having it sort "in place" is 
  // a good idea, too.

  // You will probably find the method str1.compareTo(str2) useful for comparing
  // strings. Do not use <, ==, or >.
  
  public void sort()
  {
    // uses Heapsort since it sorts in-place as compared to merge sort
	// and has O(nlogn) worst case time complexity which is better
	// than quicksort's, so it's asymptotically pretty efficient
	  
	  Heap arrHeap = new Heap(arr, size, max);
	  arrHeap.heapSort();
	  //using arraycopy to shift the sorted heap back 1 position (since
	  // the sorted heap starts at position 1 instead of 0 to allow
	  // for easier calculations.
	  System.arraycopy(arrHeap.value, 1, arr, 0, size);
  }

  
  public int getSize() 
  {
    // returns the size of the array arr
    return size;
  }

  // Method "print" prints the contents of arr, one entry per line

  public void print() 
  {
    for(int i = 0; i < size; i++)
      System.out.println(arr[i]);
  }

  public boolean findLinearSearch(String txt)
  { // Method "findLinearSearch" returns true/false depending on whether 
	// the string given as an argument is contained in arr or not. It looks 
	// for the string by scanning through the elements of arr from position 
	// 0 to size-1.
	  boolean txtFound = false;
	  for (int i = 0; i < size; i++)
	  {
		  if (arr[i].compareTo(txt) == 0)
			  {
			  	txtFound = true;
			  	break;
			  }
    			
	  }
	  return txtFound;
  }



  public boolean findBinarySearch(String txt)
  {	// Method "findBinarySearch" returns true/false depending on whether the 
	// string given as an argument is contained in arr or not. It looks for 
	// the string using binary search.
    return findBinarySearch(txt, 0, size -1);
  }

  public boolean findBinarySearch(String txt, int l, int r)
  {	  if (r < l)
		  return false;
	  else
	  {
		  int mid = (l+r)/2;
		  if (txt.compareTo(arr[mid]) < 0)
			  return findBinarySearch(txt, l, mid - 1);
		  else if (txt.compareTo(arr[mid]) > 0)
			  return findBinarySearch(txt, mid + 1, r);
		  else
			  return true;			  
	  }
  }


}


class Heap{
	// The Heap class is a data structure consisting of 2 pieces of data:
    // an array and a heapsize. However, because we let the first array 
    // index be 1, the length of the array will be one more than its size.
    // So we also create a variable containing its size.
    public int heapSize;
    public int size;
    public String[] value;

    public Heap(String[] inputArray, int heapSize, int size)
    // constructor method 
    // initialises the heapSize and size and creates the String[] array
    // uses arraycopy to shift the inputArray down by 1 since
    // the heap starts at position 1 instead
    {
        this.heapSize = heapSize;
        this.size = size;
        String[] emptyFirstElement = new String[1];
        this.value = new String[size+1];
        System.arraycopy(emptyFirstElement, 0, value, 0, 1);
        System.arraycopy(inputArray, 0, value, 1, size);
        
    }

    public void siftdown(int i)
    { //Siftdown compares the child nodes using the .compareTo order relation instead
        String temp = value[i];
        int child = maxchild(i);
        while (child != 0 && temp.compareTo(value[child]) < 0)
        {
            value[i] = value[child];
            i = child;
            child = maxchild(i);
        }
        value[i]=temp;
    }

    int maxchild(int i)
    { // Returns the child of node i with largest value.
      // Returns 0 if i has no children.

        int child = 2*i;
    	if (child > heapSize)
    		return 0;
    	if (child == heapSize)
    		return child;
    	if (value[child].compareTo(value[child+1]) >= 0)
    		return child;
    	else
    		return child + 1;
    }

    public void buildHeap()
    {
        for (int i = heapSize/2; i >= 1; i--)
    		siftdown(i);
    }

    public void heapSort()
    {
        buildHeap();
    	while (heapSize > 1)
    	{
    		String temp = value[1];
    		value[1] = value[heapSize];
    		value[heapSize] = temp;
    		heapSize--;
    		siftdown(1);    		
    	}
    		
    }
    
}
