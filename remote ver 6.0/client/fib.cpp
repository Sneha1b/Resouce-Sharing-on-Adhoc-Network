#include <iostream>

using namespace std;

template <unsigned N>
struct fib{
	static const int value = fib<N-1>::value + fib<N-2>::value;
};

template <>
struct fib<1>{
	static const int value = 1;
};

template <>
struct fib<0>{
	static const int value = 1;
};

int main(){
	cout << "Fib of 5 is:" << fib<5>::value <<endl;
	cout << "Fib of 6 is:" << fib<6>::value <<endl
	cout << "Fib of 7 is:" << fib<7>::value <<endl;
	return 0;
}
