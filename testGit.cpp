#include<iostream>
#include<map>
#include<algorithm>
using namespace std;
int maxn = 100000;
struct node {
	int x, y;
	int w;
};
bool cmp(node a, node b) {
	return a.w < b.w;
}
class tree {
private:
	string s[100];
	int** graph;
	int n,m;
	int sum;
	int* f;
	node* pq;
	map<string, int> mp;
public:
	tree() {
		string s1, s2;
		int  tn, w;
		cin >> tn;
		n = tn;
		for (int i = 0;i < n;i++) {
			cin >> s[i];
			mp[s[i]] = i;
		}
		graph = new int* [n];
		for (int i = 0;i < n;i++)
			graph[i] = new int[n];
		for (int i = 0;i < n;i++) {
			for (int j = 0;j < n;j++)
				graph[i][j] = maxn;
		}
		cin >> m;
		pq = new node[m];
		for (int i = 0;i < m;i++) {
			cin >> s1 >> s2 >> w;
			graph[mp[s1]][mp[s2]] = w;
			graph[mp[s2]][mp[s1]] = w;
			pq[i].x = mp[s1];
			pq[i].y = mp[s2];
			pq[i].w = w;
		}
	}
	void prim(string s1) {
		sum = 0;
		int v = mp[s1];
		int* low = new int[n];
		int* clo = new int[n];
		for (int i = 0;i < n;i++) {
			clo[i] = v;
			low[i] = graph[v][i];
		}
		low[v] = 0;
		for (int c = 1;c < n;c++) {
			int minn = maxn;
			int minid = 0;
			for (int i = 0;i < n;i++) {

				if (low[i] != 0 && low[i] < minn) {
					minn = low[i];
					minid = i;
				}
				//cout << "minid=" << minid << endl;
			}
			cout << low[minid] << endl;
			cout << s[clo[minid]] << '-' <<s[minid] << endl;
			sum += low[minid];
			low[minid] = 0;
			for (int i = 0;i < n;i++) {
				if (low[i] != 0 && graph[minid][i] < low[i]) {
					low[i] = graph[minid][i];
					clo[i] = minid;
				}
			}
		}
		cout << "sum=" << sum << endl;
	}
	int father(int n) {
		//if (n == f[n]) return n;
		return n == f[n] ? n : f[n] = father(f[n]);
	}
	void krus() {
		int k = 0;
		sum = 0;
		f = new int[n];
		for (int i = 0;i < n;i++) {
			f[i] = i;
		}
		sort(pq, pq + m, cmp);
		for (int i = 0;i < m;i++) {
			if (k == n - 1) break;
			if (father(pq[i].x) != father(pq[i].y)) {
				sum += pq[i].w;
				//f[pq[i].y] = f[pq[i].x];
				//f[pq[i].y] = father(pq[i].x);
				int p = father(pq[i].x);
				int q = father(pq[i].y);
				if (p != q) f[q] = p;
				cout << s[pq[i].x] << ' ' << s[pq[i].y] << endl;
				k++;
			}
		}
		cout << "sum=" << sum << endl;
	}

};
int main()
{
	tree ob;
	string s;
	cin >> s;
	ob.krus();
	return 0;
}