'''
Created on 2013/12/17

@author: Justin
'''
from LinkedList import LinkedList 
class Node(object):
    def _init_(self):
        self.element = None
        self.parent = None
        self.child_list = None
        self.have_child = False
    def set_child(self):
        self.child_list =  LinkedList()
        self.have_child = True
    def add_child(self,node):
        self.child_list.add_front(node)
        
class Tree(object):
    def __init__(self):
        self.root = None
        self.size = 0
        self.pointer  = self.root
    def set_root(self,e):
        self.root = Node()
        self.root.element = e
        self.size = 1
        self.pointer  = self.root
    def set_childlist(self):
        self.pointer.set_child()
    def child_add_element(self,e):
        v = Node()
        v.parent = self.pointer
        v.element = e
        v.child_list = None
        self.pointer.add_child(v)
        self.size+=1
    def pointer_down(self,i):
        self.pointer = self.pointer.child_list.get(i)
    def pointer_up(self):
        self.pointer = self.pointer.parent
        
    def preorder_print(self , x):
        print(x.element,end = " ")
        if (x.child_list is not None):
            print("(",end = " ")
            for i in range(x.child_list.size):
                self.preorder_print(x.child_list.get(i))
            print(")",end = " ")
'''test space 
tree = Tree()
tree.set_root("root")
tree.set_childlist()
tree.child_add_element("A")
tree.child_add_element("B")
tree.child_add_element("C")
tree.child_add_element("D")
tree.child_add_element("E")

tree.pointer_down(0)
print(tree.pointer.child_list)
tree.set_childlist()
tree.child_add_element("a1")
tree.child_add_element("a2")
tree.child_add_element("a3")

tree.pointer_down(2)
print(tree.pointer.element)
print(tree.size)
tree.preorder_print(tree.root)
'''
