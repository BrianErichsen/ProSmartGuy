//Author: Brian Erichsen Fagundes
//University of Utah - Masters in Software Development
//Spring of 2024

#include <iostream>
#include <vector>

class RC4 {
private:
    std::vector<int> S;//s-box vector
    int i;
    int j;

public:
//constructor that takes a key
    RC4(std::vector<int> key) {
        S.resize(256);
        for (int i = 0; i < 256; i++) {
            S[i] = i;
        }
        j = 0;//sets i and j to 0
        for (int i = 0; i < 256; i++) {
            //updates j by key and s box
            j = (j + S[i] + key[i % key.size()]) % 256;
            std::swap(S[i], S[j]);
        }
        i = 0;
        j = 0;
    }//end of constructor bracket

//method to to go to the next byte of keystream
int getNextByte() {
    //PRGA
    i = (i + 1) % 256;
    j = (j + S[i]) % 256;
    std::swap(S[i], S[j]);
    int temp = (S[i] + S[j]) % 256;

    return S[temp];
}
};//end of class RC4 bracket

int main () {
    std::vector<int> key = {0x1a, 0x2b, 0x4d, 0x5e};
    std::string plaintext = "Secret message";
    std::vector<int> ciphertext;
    //sets up rc4 encryption with example key
    RC4 encrypt1(key);
    //does the encryption
    for (char ch : plaintext) {
        ciphertext.push_back(ch ^ encrypt1.getNextByte());
    }

    std::string decrypt;
    RC4 decrypt1(key);
    //does the decryption
    for (char ch : ciphertext) {
        decrypt += (char) (ch ^ decrypt1.getNextByte());
    }
    //prints original plaintext and ciphertext decrypted
    std::cout << "Text was: " << plaintext << std::endl;
    std::cout << "Decrypted message is: " << decrypt << std::endl;

    //attempt to decrypt message with different key -->
    std::vector<int> key2 = {0x10, 0x12, 0x22, 0x34, 0x45};

    std::string decryptText2;
    RC4 rcDecrypt2(key2);
    //performs the decryption
    for (int i : ciphertext) {
        decryptText2 += (char)(i ^ rcDecrypt2.getNextByte());
    }
    //prints new decryption done with different key
    std::cout << "Decryption with different key: " << decryptText2 <<
    std::endl;
    //---------------------------------------------------------------
    //verify that encrypting 2 messages using same keystream is insecure

    std::string message1 = "I am learning how to be better";
    std::string message2 = "You are learning how to be better";
    std::vector<int> ciphertext1;
    std::vector<int> ciphertext2;

    RC4 stream(key);
    //encrypts first message
    for (char ch : message1) {
        ciphertext1.push_back(ch ^ stream.getNextByte());
    }
    //encrypts second message
    for (char ch : message2) {
        ciphertext2.push_back(ch ^ stream.getNextByte());
    }
    // XOR the 2 encrypted messages
    std::vector<int> xorResult;
    for (size_t i = 0; i < ciphertext1.size(); ++i) {
        xorResult.push_back(ciphertext1[i] ^ ciphertext2[i]);
    }

    std::cout << "XOR of the 2 encrypted messages: " << std::endl;
    for (int i : xorResult) {
        std::cout << i << " ";
    }

    std::cout << std::endl;

    //-----------------------------------------------------------
    //modify part of message using a bit flipping

    auto encrypt_plaintext = [](const std::string& message, const std::vector<int>
    & key) {
        RC4 rc4(key);//given the key - initialises rc4
        std::vector<int> encrypted_plaintext; //stores encrypted message
        for (char ch : message) { //loops over message and encrypts it
            encrypted_plaintext.push_back(ch ^ rc4.getNextByte());
        }
        return encrypted_plaintext; //returns new encrypted message
    };

    auto decrypt_plaintext = [](const std::vector<int>& encrypted_message,
    const std::vector<int>& key) {
        RC4 rc4(key); //given the key - initialises rc4
        std::string decrypted_message;//stores decrypted message
        for (int b : encrypted_message) { //loops over message and decrypts it
            decrypted_message += (char)(b ^ rc4.getNextByte());
        }
        return decrypted_message;//returns new decrypted message
    };

    //initialises orignal msg and its modified version
    std::string original_msg = "Your salary is $1000";
    std::string modified_msg = "Your salary is $9000";
    //does the ecryption for the original message
    std::vector<int> encrypted_msg66 = encrypt_plaintext(original_msg, key);
    std::vector<int> modified_encrypted_msg;//stores new encrypted msg
    //does the bit flipping attack
    for (size_t i = 0; i < encrypted_msg66.size(); ++i) {
        modified_encrypted_msg.push_back(encrypted_msg66[i] ^ original_msg[i] ^
        modified_msg[i]);
    }

    //decrypts the altered message
    std::string decrypted_changed_msg = decrypt_plaintext(modified_encrypted_msg, key);
    //prints out original msg / decrypted altered message
    std::cout << "Message was: " << original_msg << std::endl;
    std::cout << "Decrypted altered message: " << decrypted_changed_msg
    << std::endl;
    std::cout << std::endl;

    //if reached here --- no issues found // return with a 0 status exit
    return 0;
}//end of main bracket