import matplotlib.pyplot as plt

# Define threshold values
thresholds = ['array_stack', 'linked_list']

# Plotting the data for each threshold
for threshold in thresholds:
    # Read data from file
    filename = f'pop_{threshold}.csv'
    with open(filename, 'r') as file:
        lines = file.readlines()

    # Extracting data
    sizes, times = zip(*(map(float, line.split()) for line in lines))

    # Plotting the data with a different color for each threshold
    plt.plot(sizes, times, marker='o', label=f'Pop method for: {threshold}')

# Add labels and legend
plt.title('ArrayList vs LinkedList Experiment')
plt.xlabel('Size')
plt.ylabel('Average Time (nanoseconds)')
plt.legend()

# Show the plot
plt.show()