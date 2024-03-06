//Created by Brian Erichsen Fagundes//
//Feb of 2024 - Encryption bad block cipher example
// MSD - Spring of 2024
#include <iostream>
#include <string>
#include <array>
#include <random>
#include <algorithm>

//beg-- of  functions to convert plaintext into ciphertext

//defines blocks and its size
using Block = std::array<uint8_t, 8>;

const int table_size = 256;
const int block_size = 8;

//performs xor operation among two blocks
Block xorBlocks(const Block& block1, const Block& block2) {
    Block result;
    for (int i = 0; i < 8; ++i) {
        result[i] = block1[i] ^ block2[i];
    }
    return result;
}

// Function to generate the encryption key based on the given password
Block generateKey(const std::string& password) {
    Block key = {0, 0, 0, 0, 0, 0, 0, 0};
    for (size_t i = 0; i < password.length(); ++i) {
        key[i % 8] ^= password[i];
    }
    return key;
}

// Function to generate a random substitution table using Fisher-Yates shuffle
std::array<uint8_t, 256> generateSubstitutionTable() {
    std::array<uint8_t, 256> table;
    for (int i = 0; i < 256; ++i) {
        table[i] = static_cast<uint8_t>(i);
    }
    std::random_device rd;
    std::mt19937 gen(rd());
    for (int i = 255; i > 0; --i) {
        std::uniform_int_distribution<> dis(0, i);
        int j = dis(gen);
        std::swap(table[i], table[j]);
    }
    return table;
}

// Function to invert a substitution table
std::array<uint8_t, 256> invertSubstitutionTable(const std::array<uint8_t, 256>& table) {
    std::array<uint8_t, 256> invertedTable;
    for (int i = 0; i < 256; ++i) {
        invertedTable[table[i]] = static_cast<uint8_t>(i);
    }
    return invertedTable;
}

// Function to perform the substitution step using the provided table
Block substitute(const Block& block, const std::array<uint8_t, 256>& table) {
    Block result;
    for (int i = 0; i < 8; ++i) {
        result[i] = table[block[i]];
    }
    return result;
}

// Function to perform the rotation step
Block rotate(const Block& block) {
    Block result;
    for (int i = 0; i < 8; ++i) {
        result[i] = (block[i] << 1) | (block[(i + 1) % 8] >> 7);
    }
    return result;
}

// Function to perform the reverse rotation step
Block reverseRotate(const Block& block) {
    Block result;
    for (int i = 0; i < 8; ++i) {
        result[i] = (block[i] >> 1) | (block[(i + 7) % 8] << 7); // Reverse rotation
    }
    return result;
}

// Encryption algorithm
Block encrypt(const Block& message, const Block& key, const std::array<std::array<uint8_t, 256>, 8>& substitutionTables) {
    Block state = message;
    for (int round = 0; round < 16; ++round) {
        state = xorBlocks(state, key);
        for (int i = 0; i < 8; ++i) {
            state = substitute(state, substitutionTables[i]);
        }
        state = rotate(state);
    }
    return state;
}

// Decryption algorithm
Block decrypt(const Block& ciphertext, const Block& key, const std::array<std::array<uint8_t, 256>, 8>& reverseSubstitutionTables) {
    Block state = ciphertext;
    for (int round = 0; round < 16; ++round) {
        state = reverseRotate(state);
        for (int i = 7; i >= 0; --i) {
            state = substitute(state, reverseSubstitutionTables[i]);
        }
        state = xorBlocks(state, key);
    }
    return state;
}


int main () {
    //defines a specific password
    std::string password = "IThinkEveryoneIsSpecial";

    Block key = generateKey(password); //creates key based on password

    //creates substitution tables
    std::array<std::array<uint8_t, table_size>, block_size> tables;

    //creates the substitution tables
    for (int i = 0; i < block_size; ++i) {
        tables[i] = generateSubstitutionTable();
    }

    std::array<std::array<uint8_t, table_size>, block_size> reverseTables;
    //iterates over tables per block size and reverses each table per block
    for (int i = 0; i < block_size; i++) {
        reverseTables[i] = invertSubstitutionTable(tables[i]);
    }
    //creates a msg to be encrypted
    Block message = {8, 7, 6, 5, 4, 3, 2, 1};
    Block ciphertext = encrypt(message, key, tables);

    std::cout << "Encrypted message is ";
    for (int i = 0; i < block_size; i++) {
        std::cout << static_cast<int>(ciphertext[i]) << " ";
    }
    std::cout << std::endl;
    //tamper with ciphertext (modify one bit)
    ciphertext[0] ^= 1;

    ciphertext[0] ^= 1;

    Block back_to_plainText = decrypt(ciphertext, key, reverseTables);
    std::cout << "Decrypted message is ";
        for (int i = 0; i < block_size; i++) {
            std::cout << static_cast<int>(back_to_plainText[i]) << " ";
        }
    std::cout << std::endl;

    std::cout << "Original message: ";
    for (int i = 0; i < block_size; ++i) {
        std::cout << static_cast<int>(message[i]) << " ";
    }
    std::cout << std::endl;

    return 0;
}//end of main bracket