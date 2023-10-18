// Selection Sort
function FirstSelectionSort(array) {
    for (let i = 0; i < array.length - 1; i++) {
        //We initialize a variable minIndex to i. This variable will keep track of the index of the minimum element found during each pass of the array.
        let minIndex = i;
        //We start another loop nested inside the outer loop. This inner loop iterates through the array, starting from the element just after the current i
        for (let j = i + 1; j < array.length; j++) {
            if (array[j] < array[minIndex]) {
                minIndex = j;
            }
        }
        /*After the inner loop completes, we check if minIndex has changed from 
        its initial value i. If it has changed, it means we've found a smaller 
        element than the one at index i.*/
        if (minIndex !== i) {
            /*minIndex has changed, we swap the element at index i with the minimum 
            element found at minIndex. This moves the minimum element to 
            its correct position in the sorted portion of the array.*/
            [array[i], array[minIndex]] = [array[minIndex], array[i]];
        }
    }
}

// Test selectionSort with different data types
const numbers = [5, 3, 8, 1, 7];
const strings = ["flower","pen", "eggs","orange", "pasta" ];
const floats = [1.0, 2.4, 0.1, 5.6, 5.5]
const mixedData = [7, "cup", 3.4, "Pink", 1, "cherry", "Book", "letter"];

// Sort numbers in ascending order
FirstSelectionSort(numbers);
console.log("Sorted numbers:", numbers);

// Sort strings in alphabetical order
FirstSelectionSort(strings);
console.log("Sorted strings:", strings);

// Sort floats in ascending order
FirstSelectionSort(floats);
console.log("Sorted floats:", floats);

// Sort mixed data
FirstSelectionSort(mixedData);
console.log("Sorted mixed data:", mixedData);




// =====================================  Second Part =====================================//
//The selectionSort function takes an array to be sorted 
//and a compareFunction that defines the comparison logic between elements in the array.
function selectionSort(array, compareFunction) {
    //We start a loop that iterates through the array, from the first element (index 0) to the second-to-last element. The reason we stop at the second-to-last element is that the last element will already be in its correct position when all others are sorted.
    for (let i = 0; i < array.length - 1; i++) {
        //We initialize a variable minIndex to i. This variable will keep track of the index of the minimum element found during each pass of the array.
        let minIndex = i;
        //We start another loop nested inside the outer loop. This inner loop iterates through the array, starting from the element just after the current i
        for (let j = i + 1; j < array.length; j++) {
            /*Within the inner loop, we use the compareFunction to compare the 
            element at index j with the element at minIndex. If the result of the 
            comparison is less than 0, it means that the element at index j is 
            smaller (according to the comparison logic) than the current minimum 
            element at minIndex.*/
            if (compareFunction(array[j], array[minIndex]) < 0) {
                /*If we find a smaller element at index j, we update minIndex to 
                be j, indicating that we've found a new minimum element in the 
                remaining unsorted portion of the array.*/
                minIndex = j;
            }
        }
        /*After the inner loop completes, we check if minIndex has changed from 
        its initial value i. If it has changed, it means we've found a smaller 
        element than the one at index i.*/
        if (minIndex !== i) {
            /*minIndex has changed, we swap the element at index i with the minimum 
            element found at minIndex. This moves the minimum element to 
            its correct position in the sorted portion of the array.*/
            [array[i], array[minIndex]] = [array[minIndex], array[i]];

        }
        /*The outer loop continues, and the process is repeated for the next 
        unsorted element until the entire array is sorted.*/
    }
}


// =======================   Helper function to find minimum location   ========================//

/*The findMinLocation function is a helper function used in the selection sort 
algorithm to find the index of the minimum element within a specific portion of 
an array.*/

/*The findMinLocation function takes three parameters:
array: The array in which we want to find the minimum element's index.
startIndex: The index from which to start the search for the minimum element.
compareFunction: A function that defines the comparison logic to determine the order of elements.*/
function findMinLocation(array, startIndex, compareFunction) {
    //We initialize a variable minIndex to startIndex. 
    //This variable will keep track of the index of the minimum element found during the search.
    let minIndex = startIndex;
    /*We start a loop that iterates through the array, beginning from the element 
    just after the startIndex*/
    for (let i = startIndex + 1; i < array.length; i++) {
        /*Within the loop, we use the compareFunction to compare the element at index i with 
        the element at the current minIndex. If the result of the comparison is less than 0, 
        it means that the element at index i is smaller (according to the comparison logic) 
        than the element at the current minIndex.*/
        if (compareFunction(array[i], array[minIndex]) < 0) {
            /*If we find a smaller element at index i, we update minIndex to be i, 
            indicating that we've found a new minimum element within the portion of the array being searched.*/
            minIndex = i;
        }
    }
    return minIndex;
}

// =======================   Creating Different Data Type Arrays   ========================//

// Test selectionSort with different data types and comparators
const integers = [5, 3, 8, 1, 7];
const string = ["pillow","pen", "Apple","orange", "hair" ];
const people = [
    { first: "Rylie", last: "Byers" },
    { first: "Brian", last: "Fagundes" },
    { first: "Mina", last: "Akbari" },
    { first: "Alice", last: "Byers" },
    { first: "Alice", last: "Smith" },
];


// ===============================   Printing to the Console   =============================//

// Sort numbers in ascending order
/*This function compares two numbers a and b by subtracting b from a. 
If the result is negative, it means a is less than b, and if it's positive, 
it means a is greater. The selectionSort function uses this comparison function to sort 
the numbers array in ascending order. After sorting, the sorted numbers array is logged to 
the console.*/
selectionSort(integers, function(a, b) {
    return a - b;
});
console.log("Sorted numbers:", numbers);

// Sort strings in alphabetical order
/*This function uses the localeCompare method to compare two strings a and b. 
The method returns a negative value if a comes before b in lexicographic order, 
a positive value if a comes after b, and 0 if they are equal. The selectionSort 
function uses this comparison function to sort the strings array in alphabetical order.*/
selectionSort(strings, function(a, b) {
    return a.localeCompare(b);
});
console.log("Sorted strings:", strings);

// Sort people by last name and then by first name
/*This function first checks if the last names (a.last and b.last) are equal. 
If they are, it uses localeCompare to compare the first names (a.first and b.first). 
This ensures that the people are sorted primarily by last name and then by first name. 
After sorting, the sorted people array is logged to the console.*/

// Custom Comparator for Sorting People by Last Name, Breaking Ties with First Name
function sortByLastNameAndFirstName(a, b) {
    // Compare last names first
    const lastNameComparison = a.last.localeCompare(b.last);

    if (lastNameComparison === 0) {
        // If last names are the same, compare first names
        return a.first.localeCompare(b.first);
    }

    return lastNameComparison;
}

// Custom Comparator for Sorting People by First Name, Breaking Ties with Last Name
function sortByFirstNameAndLastName(a, b) {
    // Compare first names first
    const firstNameComparison = a.first.localeCompare(b.first);

    if (firstNameComparison === 0) {
        // If first names are the same, compare last names
        return a.last.localeCompare(b.last);
    }

    return firstNameComparison;
}


// Sort people by last name and first name
selectionSort(people, sortByLastNameAndFirstName);
console.log("Sorted by Last Name and First Name:");
console.log(people);

// Sort people by first name and last name
selectionSort(people, sortByFirstNameAndLastName);
console.log("Sorted by First Name and Last Name:");
console.log(people);







//======================================    Questions   ========================================//
/*
1- What happens in each case when testing the sorting function 
with different data types, including strings with mixed capital and lowercase letters?

When we sort arrays of integers and floating-point numbers, the sorting function will 
arrange them in ascending order.
When sorting an array of strings, the function will arrange them in lexicographical order, 
considering capitalization for example, capital letters come before lowercase letters.
If we sort an array with a mix of data types (integers, strings, floats), it will still work, 
but the order might not make much sense, as the function isn't designed to handle such mixed 
data effectively.



2- What happens when you change the comparison in your "compareTo" function from "<" to ">" in the 
comparator function?

If you change the comparison from "<" to ">", it will reverse the order of sorting. 
The sorting function will sort the elements in descending order instead of ascending order.*/