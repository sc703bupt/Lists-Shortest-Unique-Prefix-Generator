## Lists Shortest Unique Prefix Generator
### What Is This Tool For
Lists Shortest Unique Prefix Generator(we call it `LSUPG` for short), is a tool for generating shortest unique prefix of lists.
Let's say we have some lists of numbers:

> List 1: 1, 2, 3, 4, 5   
> List 2: 3, 5, 2, 6   
> List 3: 3, 4, 2, 7  
> List 4: 1, 2, 3, 4  
> List 5: 1, 3, 8  
> List 6:

What we want to find is the shortest prefix that can uniquely represent for each list. In this case, we find

> List 1 LSUP: 1, 2, 3, 4, 5  
> List 2 LSUP: 3, 5   
> List 3 LSUP: 3, 4  
> List 4 LSUP: null  
> List 5 LSUP: 1, 3

If we use these LSUP to locate a list, we can `UNIQUELY` get one. 

However, some list(e.g. List 4 returns null) may not have one, simply because it is `not long enough` to be different from others. 

Another special case is because the sequence is empty, like List 6 returns nothing instead of null.

### Why Building Up This Tool
In a precedent project, i need to find LSUP of many numerical sequences, which brings big challenge for my laptop.

Generally, we can build a tree for seeking such a LSUP. But consider a large set of long lists as data set, we will get a very broad and deep tree which may deplete your memory.  

Starting from this, LSUPG uses a `"file + memory"` model in order to handle large data set case, avoiding out of memory disaster.

### Is It Only For Numerical Sequence
In fact, when i first build up this tool, it works for numerical sequence only. But i extend it for general type. The only requirement for the user is to overload the `toString()` method, because we will use the String object for comparison.

But remember, `DONT` use , and other symbols which can be not be used in file/folder name in return value of the `toString()` method .

### What Can I Use It For
Well...I don't know, maybe generating a digest for many articles or a index for data set, all depends on you.

### API Usage
Very simple. Suppose you have a self-defined Class MyType and its toString() has been overridden.
```java
Map<Integer, List<MyType>> inputMap = getInputMap();
ListShortestUniquePrefixGenerator<MyType> generator = new ListShortestUniquePrefixGenerator<MyType>();
Map<Integer, List<MyType>> outputMap = generator.generate(inputMap);
```

### Configuration
You can revise these in `config.properties`.  
`WORK_PLACE_PATH`: a work place for in-file mode  
`MAX_COUNT_OF_SEQUENCES_IN_SINGLE_FILE`: if sequences contained in single file less than this value, program will use in-memory mode.   
`FILE_PATH_LENGTH_LIMIT`: windows file path length limit.  
`INIT_RECURSION_LEVEL`: constant.  

### Required Lib
log4j-1.2.14.jar  
commons-lang3-3.4.jar  

### I Find A Bug || I Have Good Idea For This Tool
Tell me by sending email to sc1_1#163.com, you are appreciated.