def parse_ping_data(file_path):
    round_trip_delays = []

    with open(file_path, 'r') as file:
        for line in file:
            # Extract round-trip time from each line
            time_index = line.find('time=')
            if time_index != -1:
                rtt_start = time_index + len('time=')
                rtt_end = line.find(' ms', rtt_start)
                if rtt_end != -1:
                    rtt = double(line[rtt_start:rtt_end])
                    round_trip_delays.append(rtt)

    return round_trip_delays

def calculate_average_queuing_delay(round_trip_delays):
    # Find the minimum round-trip delay (reference value)
    min_delay = min(round_trip_delays)

    # Subtract the minimum delay from each round-trip delay
    normalized_delays = [delay - min_delay for delay in round_trip_delays]

    # Calculate the total normalized delay
    total_normalized_delay = sum(normalized_delays)

    # Calculate the average normalized delay
    average_queuing_delay = total_normalized_delay / len(round_trip_delays)

    return average_queuing_delay

# Example usage
file_path = 'ping_output.txt'
round_trip_delays = parse_ping_data(file_path)

if round_trip_delays:
    average_queuing_delay = calculate_average_queuing_delay(round_trip_delays)
    print(f"Average Round-Trip Queuing Delay: {average_queuing_delay:.2f} ms")
else:
    print("No valid round-trip delays found in the data.")
