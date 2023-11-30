import matplotlib.pyplot as plt

# Define threshold values
thresholds = ['balanced', 'unbalanced']

# Plotting the data for each threshold
for threshold in thresholds:
    # Read data from file
    filename = f'bst_{threshold}_contains.csv'
    with open(filename, 'r') as file:
        lines = file.readlines()

    # Extracting data
    sizes, times = zip(*(map(float, line.split()) for line in lines))

    # Plotting the data with a different color for each threshold
    plt.plot(sizes, times, marker='o', label=f'Contains method for: {threshold}')

# Add labels and legend
plt.title('BST experiment for contains method TreeSet vs BinarySeachTree')
plt.xlabel('Size')
plt.ylabel('Average Time (nanoseconds)')
plt.legend()

# Show the plot
plt.show()