# noinspection PyInterpreter
import matplotlib.pyplot as plt

# Read data from file
with open('mergesort_threshold_MAX.csv', 'r') as file:
    lines = file.readlines()

# Extracting data
# noinspection PyInterpreter
sizes, times = zip(*(map(float, line.split()) for line in lines))

# Plotting the data
plt.plot(sizes, times, marker='o')
plt.title('Mergesort Threshold of MAX_VALUE (insertion sort) Running Time vs. Size')
plt.xlabel('Size')
plt.ylabel('Average Time (nanoseconds)')

# Show the plot
plt.show()