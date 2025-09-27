## Analysis of Divide\-and\-Conquer Algorithms

This repository contains implementations and performance analysis of four classic divide\-and\-conquer algorithms as part of a course assignment\. The focus is on robust implementation, metric collection \(time, comparisons, recursion depth\), and comparing theoretical analysis with empirical results\.

---

### Architecture Notes & Implementation Details

My implementation strategy focused on both correctness and efficiency, paying close attention to memory and stack depth as required\.

**Metric Collection**: A simple `Metrics` class is passed through the algorithm calls\. This avoids global state and makes the tracking thread\-safe and encapsulated within a single run\. It tracks comparisons, allocations \(e\.g\., for buffers or helper arrays\), and `maxDepth` of recursion\.

**MergeSort**: To control allocations, a single reusable buffer array is allocated once in the main `mergeSort` function and passed down through the recursion\. For small subarrays \(size <= 16\), the algorithm switches to Insertion Sort, which has lower constant\-factor overhead and is more cache\-friendly for small `n`\.

**QuickSort**: To prevent worst\-case stack overflow on adversarial inputs \(like a sorted array\), the implementation is robust in two ways\. First, it uses a randomized pivot to make the worst\-case partitioning scenario statistically improbable\. Second, it only recurses on the smaller partition, while the larger partition is handled iteratively\. This guarantees a maximum stack depth of `O\(log n\)`\.

**Deterministic Select**: This algorithm finds the k\-th smallest element in linear time\. The key is the "Median of Medians" pivot selection strategy, which guarantees a good partition\. The implementation recurses only into the partition that contains the k\-th element, which is the source of its efficiency\.

**Closest Pair of Points**: The classic `O\(n log n\)` solution is implemented\. A critical optimization is pre\-sorting the points by y\-coordinate\. Instead of re\-sorting within each recursive call, the y\-sorted list is filtered down, which is a linear\-time operation\. The "strip" check is carefully implemented to only compare each point against its next 7\-8 neighbors in the y\-sorted strip, maintaining the `O\(n\)` work per merge step\.

---

### Recurrence Analysis

Here is the theoretical running time analysis for each algorithm\.

1\. **MergeSort**:  
The recurrence relation is `T\(n\) = 2T\(n/2\) + Θ\(n\)`\. The `2T\(n/2\)` comes from the two recursive calls on half\-sized arrays, and `Θ\(n\)` is the work done by the merge procedure\. According to the Master Theorem \(Case 2\), where `a=2`, `b=2`, and `f\(n\) = n`, we have `log_b\(a\) = log_2\(2\) = 1`\. Since `f\(n\) = Θ\(n^log_b\(a\)\)`, the solution is `Θ\(n log n\)`\.

2\. **QuickSort \(Robust\)**:  
On average \(due to the randomized pivot\), the partition is expected to be balanced\. The recurrence is `T\(n\) = 2T\(n/2\) + Θ\(n\)`, which, like MergeSort, resolves to `Θ\(n log n\)`\. The worst\-case recurrence is `T\(n\) = T\(n\-1\) + Θ\(n\)`, leading to `Θ\(n^2\)`, but this is highly unlikely\. The stack\-depth\-bounding strategy does not change the time complexity but improves its practical space complexity\.

3\. **Deterministic Select \(Median\-of\-Medians\)**:  
The recurrence is `T\(n\) <= T\(n/5\) + T\(7n/10\) + Θ\(n\)`\. `T\(n/5\)` is the cost of finding the median of medians, `T\(7n/10\)` is the worst\-case size of the partition we recurse into, and `Θ\(n\)` is the work for partitioning and finding medians of groups of 5\. This does not fit the Master Theorem\. However, using Akra\-Bazzi intuition or substitution, we can show that `n/5 + 7n/10 = 9n/10 < n`, which means the work at each level of recursion decreases geometrically\. This leads to a solution of `Θ\(n\)`\.

4\. **Closest Pair of Points**:  
The recurrence relation is `T\(n\) = 2T\(n/2\) + Θ\(n\)`\. We recurse on two halves of the points \(2T\(n/2\)\), and the "strip" check takes linear time \(Θ\(n\)\)\. This is another application of the Master Theorem \(Case 2\), identical to MergeSort, giving a final time complexity of `Θ\(n log n\)`\.

---

### Performance Plots & Discussion

\(You will generate data using the CLI runner, import it into a spreadsheet or plotting tool, and paste the resulting images here\.\)

#### Time vs\. Input Size \(n\)

**Discussion**:  
This plot should show **select** as a clear linear line, while **mergesort**, **quicksort**, and **closest** should show `n log n` curves\. At small `n`, constant factors dominate, but the asymptotic behavior should become clear as `n` grows\.

#### Recursion Depth vs\. Input Size \(n\)

**Discussion**:  
Both **MergeSort** and the robust **QuickSort** should exhibit a clear logarithmic relationship between `n` and recursion depth\. QuickSort's depth might be slightly higher due to the randomization, but it should remain bounded\. Deterministic Select's depth will also be logarithmic\.

---

### Constant\-Factor Effects \(Cache, GC\)

**Cache**:  
The insertion sort cutoff in MergeSort is a great example of optimizing for cache\. For small arrays that fit in cache, the simple, loop\-heavy structure of inserti
