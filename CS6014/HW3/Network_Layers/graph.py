import matplotlib.pyplot as plt

# Read data from the output file
data = []
with open("output.txt", "r") as file:
    for line in file:
        parts = line.strip().split(",")
        data.append((int(parts[0]), int(parts[1])))

# Separate x and y data
x_data = [item[0] for item in data]
y_data = [item[1] for item in data]

# Plot the data
plt.plot(x_data, y_data, marker='o', linestyle='-')
plt.xlabel('Network Size')
plt.ylabel('Message Count')
plt.title('Messages vs Network Size')
plt.grid(True)
plt.show()