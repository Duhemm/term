#ifdef _WIN32
#include <windows.h>
#else
#include <unistd.h>
#include <sys/ioctl.h>
#endif

#include "include/term_NativeTermSize.h"

/*
 * Class:      term_NativeTermSize
 * Method:     rawTermSize
 * Signature:  ()I
 */
JNIEXPORT jint JNICALL Java_term_NativeTermSize_rawTermSize
  (JNIEnv *env, jobject obj) {
    unsigned short cols;
    unsigned short rows;
    int err;

    #ifdef _WIN32
    // https://stackoverflow.com/a/12642749
    CONSOLE_SCREEN_BUFFER_INFO csbi;
    GetConsoleScreenBufferInfo(GetStdHandle(STD_OUTPUT_HANDLE), &csbi);
    cols = csbi.srWindow.Right - csbi.srWindow.Left + 1;
    rows = csbi.srWindow.Bottom - csbi.srWindow.Top + 1;
    #else
    struct winsize win;
    err = ioctl (STDIN_FILENO, TIOCGWINSZ, (char *) &win);
    cols = win.ws_col;
    rows = win.ws_row;
    #endif

    if (err == 0) {
        jint encoded = (jint)cols << 16 | (jint)rows;
        return encoded;
    } else {
        return -1;
    }
}
