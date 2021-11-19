#include<iostream>
using namespace std;
void shell_sort(int a[], int n) {
	int h = 1,t;
	while (h < n / 3) {
		h = 3 * h + 1;
	}
	while (h >= 1) {
		for (int i = h; i < n ;i++)
		{
			for (int j = i;j >= h && (a[j] < a[j - h]);j -= h) {
				t = a[j];
				a[j] = a[j - 1];
				a[j - 1] = t;
			}
		}
		h = h / 3;
	}
}
void insert_sort(int a[], int n) {
	int t;
	for (int i = 0;i < n;i++) {
		int j = i;
		while (j>0)
		{
			if (a[j] < a[j - 1]) {
				t = a[j];
				a[j] = a[j - 1];
				a[j - 1] = t;
				j--;
			}
			else
			{
				break;
			}
		} 
	}
}
void print(){
    cout<<"change"<<endl;
	cout<<"reconery"<<endl;
	cout<<"merge"<<endl;
}
int main()
{
	
	int n;
	int a[100];
    int c = n + 1;
	cin >> n;
	for (int i = 0; i < n; i++)
	{
		cin >> a[i];
	}
	//insert_sort(a, n);
	shell_sort(a, n);
	for (int i = 0;i < n;i++) {
		cout << a[i];
		if (i == n - 1) {
			cout << endl;
			continue;
		}
		cout << ' ';
	}
	
}