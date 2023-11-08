import matplotlib.pyplot as plt

set_sizes = [1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576]
average_runtimes = [3.974292E-4, 1.145958E-4, 8.461659999999999E-5, 6.61334E-5, 6.949589999999999E-5, 4.86375E-5, 5.1745799999999995E-5, 8.52167E-5, 5.8479099999999994E-5, 6.43459E-5, 6.005E-5]  # Fill this list with the actual data

plt.figure(figsize=(8, 6))
plt.plot(set_sizes, average_runtimes, marker='o', label='contains() Runtime')

plt.title('contains() Runtime in a SortedSet')
plt.xlabel('Set Size')
plt.ylabel('Average Runtime (ms)')
plt.grid(True)
plt.legend()
plt.yscale('log')  # Use a logarithmic scale if the data covers a wide range of runtimes

plt.show()
