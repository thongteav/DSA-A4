/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package question1;

/**
 * A hashtable collection which uses chaining to resolve collisions and a load factor of 75%.
 * 
 * @author Thong Teav
 */
public class HashTableWithChaining<E> {    
    //variables-----------------------------------------------------------------
    private Node<E>[] elements;
    private int numElements;
    private final int INITIAL_CAPACITY = 5;
    //--------------------------------------------------------------------------
    
    //constructor---------------------------------------------------------------
    public HashTableWithChaining(){
        elements = (Node<E>[]) (new Node[INITIAL_CAPACITY]);
    }
    //--------------------------------------------------------------------------
    
    //helper methods------------------------------------------------------------
    /**
     * Adds an element to the array and checks for expansion.
     * 
     * @param o an element to be added
     * @return true if added successfully or false if otherwise
     */
    public boolean add(E o){
        if(numElements >= elements.length * 3 / 4) {//load factor of 75%
            expandCapacity();
        }
        Node<E> newNode = new Node<>(o);
        int index = o.hashCode() % elements.length;
        Node<E> firstNode = elements[index];
        if(firstNode == null){
            elements[index] = newNode;
        } else {//if it's not empty
            Node<E> currentNode = elements[index];
            Node<E> prevNode = null;
            while (currentNode != null && !currentNode.element.equals(o)){//loop until the end or when the element already exists
                prevNode = currentNode;
                currentNode = currentNode.next;
            }
            if(currentNode == null){ //when it's the end of the link
                prevNode.next = newNode;
            } else { //when the element already exists
                return false;
            }
        }
        numElements++;
        return true;
    }
    
    /**
     * Removes an element from the collection.
     * @param o the specified element to be removed
     * @return true if removal is successful or false if otherwise
     */
    public boolean remove(E o){
        boolean found = false;
        Node<E> firstNode = elements[o.hashCode() % elements.length];
        if(firstNode != null){
            if((firstNode.element == null && o == null) || (firstNode.element != null && firstNode.element.equals(o))){//found the element in the first node
                found = true;
                elements[o.hashCode() % elements.length] = firstNode.next;
                numElements--;
            } else {
                Node<E> prevNode = firstNode;
                Node<E> currentNode = firstNode.next;
                while(currentNode != null && !found){
                    if((currentNode.element == null && o == null) || (currentNode.element != null && currentNode.element.equals(o))){ //if the element is found
                        found = true;
                        prevNode.next = currentNode.next; //remove the element from the collection
                        numElements--;
                    } else {
                        prevNode = currentNode;
                        currentNode = currentNode.next;
                    }
                }
            }
        }
        return found;
    }
    
    /**
     * Checks if an element already exists in the collection.
     * 
     * @param o a specified element to be searched for
     * @return true if the element is found or false if otherwise
     */
    public boolean contains(E o){
        boolean found = false;
        Node<E> firstNode = elements[o.hashCode() % elements.length];
        if(firstNode != null){
            if((firstNode.element == null && o == null) || (firstNode.element != null && firstNode.element.equals(o))){ //check if the first element is the specified element
                found = true;
            } else {
                Node<E> prevNode = firstNode;
                Node<E> currentNode = firstNode.next;
                while(currentNode != null && !found){
                    if((currentNode.element == null && o == null) || (currentNode.element != null && currentNode.element.equals(o))){
                        found = true;
                    } else {
                        prevNode = currentNode;
                        currentNode = currentNode.next;
                    }
                }
            }            
        }
        return found;
    }
    
    /**
     * Returns the number of elements in the collection.
     * @return 
     */
    public int size(){
        return numElements;
    }
    
    /**
     * Expand the array by creating a new array and recalculate the position of the elements to be inserted into the new array
     */
    private void expandCapacity() {
        System.out.println(">>>>>Expanding array<<<<<");
        Node<E>[] largerArray = (Node<E>[]) (new Node[elements.length * 2]);
        
        for(Node<E> node : elements) {
            Node<E> prevNode = null;
            Node<E> currentNode = node;
            while(currentNode != null){
                if(prevNode != null) {
                    prevNode.next = null; //break the link from the node when it's already been inserted to the new array
                }
                Node<E> prevInsertNode = null;
                Node<E> insertNode = largerArray[currentNode.element.hashCode() % largerArray.length];
                while(insertNode != null){ //find the last available node of the new position in the array
                    prevInsertNode = insertNode;                    
                    insertNode = insertNode.next;
                }
                if(prevInsertNode == null){ //no element in the new position of the larger array
                    largerArray[currentNode.element.hashCode() % largerArray.length] = currentNode;
                } else {
                    prevInsertNode.next = currentNode; //insert the current node into the last node of linked list in the new array
                }
                prevNode = currentNode;
                currentNode = currentNode.next;
            }
        }        
        elements = largerArray; //copy the larger array into the current array
    }
    
    /**
     * Return the string representation of the link in each position of the array
     * @return 
     */
    @Override
    public String toString(){
        String output = "";
        int index = 0;
        for(Node<E> node : elements){
            output += "[" + index++ + "]: ";
            Node<E> currentNode = node;
            while(currentNode != null){
                output += currentNode.element.toString() + " ";
                currentNode = currentNode.next;
            }
            output += "\n";
        }
        return output;
    }
    
    private class Node<E>
    {
        public E element;
        public Node<E> next;
        
        public Node(E element){
            this.element = element;
            next = null;
        }
    }
    
    public static void main(String[] args){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>Empty hashtable<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println("Creating a hashtable for people");
        HashTableWithChaining<Person> peopleTable = new HashTableWithChaining<>();
        System.out.println(peopleTable);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
        
        Person p1 = new Person("Jonh");
        Person p2 = new Person("Kim");
        Person p3 = new Person("Kftie");
        Person p4 = new Person("Henry");
        Person p5 = new Person("Raymond");
        
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>Remove person from empty table<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.print("Trying to remove non-existing person 1 John: ");
        System.out.println(peopleTable.remove(p1));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
        
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>Adding people to the table<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println("Adding 5 people to the people table");
        System.out.println("Adding person 1 John");
        peopleTable.add(p1);
        System.out.println("Adding person 2 Kim");
        peopleTable.add(p2);
        System.out.println("Printing the table");
        System.out.println(peopleTable);
        System.out.println("Adding person 3 Kftie");
        peopleTable.add(p3);
        System.out.println("Adding person 4 Henry");
        peopleTable.add(p4);
        System.out.println("Adding person 5 Raymond");
        peopleTable.add(p5);
        System.out.println("Printing the table");
        System.out.println(peopleTable);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
        
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>Adding existed person<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.print("Adding person 5 Raymond again: ");
        System.out.println(peopleTable.add(p5));
        System.out.println("Printing the table");
        System.out.println(peopleTable);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
        
        Person p6 = new Person("Frank");
        Person p7 = new Person("Amy");
        Person p8 = new Person("Elly");
        Person p9 = new Person("Lily");
        Person p10 = new Person("Michael");
        
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>Adding new people<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println("Adding person 6 Frank");
        peopleTable.add(p6);
        System.out.println("Adding person 7 Amy");
        peopleTable.add(p7);
        System.out.println("Printing the table");
        System.out.println(peopleTable);
        System.out.println("Adding person 8 Elly");
        peopleTable.add(p8);
        System.out.println("Adding person 9 Lily");
        peopleTable.add(p9);
        System.out.println("Adding person 10 Michael");
        peopleTable.add(p10);
        System.out.println("Printing the table");
        System.out.println(peopleTable);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
        
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>Removing some people<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println("Removing person 2 Kim");
        peopleTable.remove(p2);
        System.out.println("Printing the table");
        System.out.println(peopleTable);        
        System.out.println("Removing person 4 Henry");
        peopleTable.remove(p4);
        System.out.println("Removing person 3 Kftie");
        peopleTable.remove(p3);
        System.out.println("Printing the table");
        System.out.println(peopleTable);
        System.out.print("Removing person 4 Henry again: ");
        System.out.println(peopleTable.remove(p4));
        System.out.println("Printing the table");
        System.out.println(peopleTable);        
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
        
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>Searching<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.print("Searching for person 1 John: ");
        System.out.println(peopleTable.contains(p1) ? "exists" : "does not exist");
        System.out.println("Searching for person 2 Kim");
        System.out.println(peopleTable.contains(p2) ? "exists" : "does not exist");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
        
    }
}
