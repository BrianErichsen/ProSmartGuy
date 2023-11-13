import matplotlib.pyplot as plt
import numpy as np

# Generate data for a logarithmic growth pattern
n = np.arange(1, 1001)  # Input sizes from 1 to 1000
log_n = np.log2(n)  # Logarithmic growth function (base 2)
#sqrt
# Plotting the data
plt.plot(n, log_n, label='O(log n)')
plt.title('Logarithmic Growth')
plt.xlabel('Input Size (n)')
plt.ylabel('Running Time or Resource Usage')
plt.legend()
plt.grid(True)
plt.show()
