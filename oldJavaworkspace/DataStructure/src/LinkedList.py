'''
Created on 2013/12/14

@author: Justin
'''
class Node(object):
    
    def __init__(self):
        self.element = None
        self.next = None
        
class LinkedList(object):
    def __init__(self):
        self.head = None
        self.size = 0
    def add_front(self, e):
        self.v = Node()
        self.v.element = e
        self.v.next = self.head
        self.head = self.v
        self.size += 1
        
    def get(self,n):
        n = self.size-1-n
        self.pointer = self.head
        for i in range(n+1):
            if i == n:
                return self.pointer.element
            self.pointer = self.pointer.next
    def size(self):
        return self.size
'''test space
    def show(self):
        node = self.head
        for i in range(self.size):
            print(node.element, end=" ")
            if not node.next == None:
                node = node.next
        
            
            
b = LinkedList()
b.add_front("a")
b.add_front("b")
b.add_front("c")
b.add_front("d")
b.add_front(1)
b.add_front(2)
b.add_front(3)
print(b.get(2))
print(b.size)
b.show()
for x in range(b.size):
    print(b.get(x))
'''