<h1>CSVFileMover</h1>
<p>
  This very simple project takes in a CSV file like below:

</p>
<table>
  <tr>
    <td>2319</td>
    <td>Adam</td>
  </tr>
  <tr>
    <td>2314</td>
    <td>Mary</td>
  </tr>
  <tr>
    <td>9841</td>
    <td>Adam</td>
  </tr>
</table>

<p>
  It is run by using the following syntax:
</p>
<p><code>java -jar CSVFileMover.jar <em>inputDirectory</em> <em>csvFile</em> [<em>fileAppendix</em>] [<em>outputFolderPath</em>]</code></p>

<h2>Usage examples</h2>
<p>
  Assume we have a CSV file called move.csv as above and we wanted to move all JPGs (.jpg) from the /Users/jamieb/Documents/miscPhotosFromFriday/ folder to the /Users/jamieb/Documents/StaffNightOut/ folder, and we wanted
  each photo to go the appropriate folder for each member of staff (where 2319 is a photo of Adam and 2314 is a photo of Mary) we would use:
</p>
<p><code>java -jar CSVFileMover.jar /Users/jamieb/Documents/miscPhotosFromFriday/ move.csv .jpg /Users/jamieb/Documents/StaffNightOut/</code></p>
