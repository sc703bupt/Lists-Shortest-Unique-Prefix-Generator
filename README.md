## Lists Shortest Unique Prefix Generator
### What Is This Tool For
Lists Shortest Unique Prefix Generator(we call it `LSUPG` for short), is a tool for generating shortest unique prefix of lists.
Let's say we have some lists of numbers:

> List 1: 1, 2, 3, 4, 5   
> List 2: 3, 5, 2, 6   
> List 3: 3, 4, 2, 7  
> List 4: 1, 2, 3, 4  
> List 5: 1, 3, 8

What we want to find is the shortest prefix that can uniquely represent for each list. In this case, we find

> List 1 LSUP: 1, 2, 3, 4, 5  
> List 2 LSUP: 3, 5   
> List 3 LSUP: 3, 4  
> List 4 LSUP: N/A  
> List 5 LSUP: 1, 3

If we use these LSUP to locate a list, we can `UNIQUELY` get one. However, some list(e.g. List 4) may not have one, simply because it is not long enough to be different from others.

### Why Building Up This Tool
In a precedent project, i need to find LSUP of many progressions, which brings big challenge for my laptop.

Generally, we can build a tree for seeking such a LSUP. But consider a large set of long lists as data set, we will get a very broad and deep tree which may deplete your memory.  

Starting from this, LSUPG uses a `"file + memory"` model in order to handle large data set case, avoiding out of memory disaster.

### Is It Only For Progression
In fact, when i first build up this tool, it works for progressions only. But i extend it for general type. The only requirement for the user is to overload the `equals` method.

### What Can I Use It For
Well...I don't know, maybe generating a digest for many articles or a index for data set, all depends on you.

### I Find A Bug || I Have Good Idea For This Tool
Tell me by sending email to sc1_1#163.com, you are appreciated.