# MRA

## Setup
Pre-requisites
- Apache Tomcat 8.0 installed
- MySQL installed with the database `swtvr` created (table creation: see below)

1. Clone/download the repo
```sh
git clone https://github.com/liangchunn/mra
```

2. Set up Tomcat sever

3. Configure the MySQL connection in `src/dbadapter/Configuration`
Here's how the file looks like
```
private static final String SERVER = "localhost";
private static final String TYPE = "mysql";
private static final int PORT = 3306;
private static final String DATABASE = "swtVR";
private static final String USER = "root";
private static final String PASSWORD = "";
```

4. Run the server and see it in action

## Database Schema
The database schema is located in `resources/*.sql`

You should run all the necessary sql command files in this directory when you're setting up the database for the first time or when something has changed. 

## Contributing
Before pushing code, you should have a GitHub account.

You should always commit your code into a new branch. This branch serves as your changes to the code, and the admin can then merge your changes into the `master` branch.
To create your own branch:
```sh
# be sure that you're in the root folder first!

# create the new branch
git branch <branch_name>

# switch to your new branch
git checkout <branch_name>

# edit your files... make changes

# stage the files you have already changed
git add -A

# commit the staged changes with a message
git commit -m "new commit message"

# commit your stuff upstream
git push -u origin <branch_name>

```