import matplotlib.pyplot as plt

# Define threshold values
thresholds = ['now']


# Plotting the data for each threshold
for threshold in thresholds:
    # Read data from file
    filename = f'average_delay_{threshold}.txt'
    with open(filename, 'r') as file:
        lines = file.readlines()

    # Extracting data
    sizes, times = zip(*(map(str, line.split()) for line in lines))

    # Plotting the data with a different color for each threshold
plt.plot(sizes, times, marker='o', label=f'Average Delay for {threshold}')

# Add labels and legend
plt.title('Traceroute from UofU to google')
plt.xlabel('IP Address')
plt.ylabel('Time in ms')
plt.xticks(rotation='vertical')
# plt.figure(figsize=(10, 6))
plt.tight_layout()
plt.legend()
# Show the plot
plt.show()