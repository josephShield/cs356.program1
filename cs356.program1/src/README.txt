To run my code simply compile it and then run it as follows:

To compile-
javac client.java
Followed by-
java client 0
or
java client 1

(Depending on whether you want to the program to execute ex0 or ex1)

For some reason the server doesn't respond with:
CS 356 Server Tue Feb 10 16:29:00 CDT 2005\n
Like it says it should on the assignment page. Instead it sends an empty string. I talked about this with you in class and you weren't sure why.
It seems from the server logs that the server never sends this to anyone. Therefore I figure the assignment page and server's behavior just don't match,
and that it's not the fault of our clients.

My program should output the server responses including the server numbers, and then "Done." when it finishes and closes all connections.

Example outputs follow, if you want more then running the program should produce the same outputs (with different server numbers obivously)

$java client 0
OK 2540 J.A.Shield 2461154
CS 356 Server Fri Sep 30 01:19:44 CDT 2011 OK 2461155
Done.


$ java client 1
OK 8680 J.A.Shield 9193985
CS 356 server sent 1232332
OK	8645202
Done.