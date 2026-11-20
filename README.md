# Library Management System

A librarian at a small school needs to track individual book copies and who has borrowed them. Each copy has a unique book ID, so multiple copies of the same title can be cataloged separately.

The console application registers members, adds books, checks books out, accepts returns, and lists availability. It rejects duplicate IDs, loans to unknown members, double checkouts, and returns by anyone other than the current borrower.

## Run locally

Use JDK 8 or newer. From the repository root, run:

```text
javac -d out LibraryApp.java
java -cp out LibraryApp
```

Enter the commands shown by the application. Separate fields with a vertical bar and use the same capitalization each time you refer to an ID. The application starts with an empty catalog and keeps records in memory for the current run.

## Classroom demonstration

Run the scripted demonstration in PowerShell:

```powershell
Get-Content demo.txt | java -cp out LibraryApp
```

In Bash or Command Prompt, use `java -cp out LibraryApp < demo.txt`.

Show the empty catalog, add a book and demonstrate duplicate ID validation. Register two members. Attempt to lend the book to an unknown member, then lend it to the first registered member and list the catalog. Show that the second member cannot borrow or return that copy while it is checked out. Return it under the first member's ID and show that the catalog lists it as available again.

In the code walkthrough, point out how the two maps enforce unique IDs and how the book's borrower ID records both availability and who can return the copy.

## Git and GitHub

Inspect the existing local history with `git log --oneline`. The application and the demo documentation are recorded in separate commits. To track future work, stage the specific files you changed with `git add` and commit them with a message describing the change.

To publish, create an empty repository in your GitHub account without an initial README. Replace the URL below with your repository URL, then run:

```text
git remote add origin https://github.com/YOUR-USERNAME/java-library-management.git
git push -u origin HEAD
```

Open the GitHub repository and show the README, source, demo input, and commit history during the presentation.
