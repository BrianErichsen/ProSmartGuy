import matplotlib.pyplot as plt

# Define threshold values
thresholds = ['good', 'mediocre', 'bad']

# Plotting the data for each threshold
for threshold in thresholds:
    # Read data from file
    filename = f'runtime_{threshold}.csv'
    with open(filename, 'r') as file:
        lines = file.readlines()

    # Extracting data
    sizes, times = zip(*(map(float, line.split()) for line in lines))

    # Plotting the data with a different color for each threshold
    plt.plot(sizes, times, marker='o', label=f'Average runtime for {threshold}')

# Add labels and legend
plt.title('Performance per different HashFunctors')
plt.xlabel('Size')
#plt.ylabel('Number of Collisions')
plt.ylabel('Average Time (nanoseconds)')
plt.legend()

# Show the plot
plt.show()