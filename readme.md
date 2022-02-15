# GitHub Timeline Demo

Simple project to demonstrate Kotlin with.

## Description

See https://blog.bitsrc.io/15-app-ideas-to-build-and-level-up-your-coding-skills-28612c72a3b1?gif=true

API’s and graphical representation of information are hallmarks of modern web applications. GitHub Timeline combines the two to create a visual history of a users GitHub activity.

The goal of GitHub Timeline is accept a GitHub user name and produce a timeline containing each repo and annotated with the repo names, the date they were created, and their descriptions. The timeline should be one that could be shared with a prospective employer. It should be easy to read and make effective use of color and typography.

Only public GitHub repos should be displayed.
User Stories

    User can enter a GitHub user name
    User can click a ‘Generate’ button to create and display the named users repo timeline
    User can see a warning message if the GitHub user name is not a valid GitHub user name.

Bonus features

    User can see a summary of the number of repos tallied by the year they were created

Useful links and resources

GitHub offers two API’s you may use to access repo data. You may also choose to use an NPM package to access the GitHub API.

Documentation for the GitHub API can be found at:

    GitHub REST API V3
    GitHub GraphQL API V4

Sample code showing how to use the GitHub API’s are:

    GitHub REST API client for JavaScript
    GitHub GraphQL API client for browsers and Node

You can use this CURL command to see the JSON returned by the V3 REST API for your repos:

> curl -u "user-id" https://api.github.com/users/user-id/repos