# Compiler and flags
CXX = g++
CXXFLAGS = -std=c++11 -Wall

# Macros for source files and headers
CXXSOURCE = $(wildcard *.cpp)
HEADERS = $(wildcard *.h)

#Macros for compiler, compiling options, and linking options
COMPILER_MACRO = CXX
COMPILE_OPTION_MACRO = CXXFLAGS
LINK_OPTION_MACRO = LDLIBS

# Default target
all: build

# Rule to print macros
printM:
	@echo "Compiler: $(CXX)"
	@echo "Compiler Flags: $(CXXFLAGS)"
	@echo "Source Files: $(CXXSOURCE)"
	@echo "Headers: $(HEADERS)"

# Rule to build the exe
build: $(CXXSOURCE:.cpp=.o)
	$(CXX) $(CXXFLAGS) $^ -o main

# Rule to compile source files into object files
%.o: %.cpp
	$(CXX) $(CXXFLAGS) -c $< -o $@

# Clean rule to remove generated files
clean:
	rm -rf *.o main

# Rule to run the executable
run: build
	./main

# Phony targets to avoid conflicts with file names
.PHONY: all printM clean