const express = require('express');
const cors = require('cors');
const multer = require('multer');
const fs = require('fs');
const PDFParser = require('pdf-parse');

const app = express();
app.use(cors());

const upload = multer({ dest: 'uploads/' }).single('file');
const port = 8080;

app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});

app.post('/upload', (req, res) => {
  upload(req, res, (err) => {
    if (err instanceof multer.MulterError) {
      return res.status(400).json({ error: 'Error uploading the file.' });
    } else if (err) {
      return res.status(500).json({ error: 'Something went wrong.' });
    }

    if (!req.file || req.file.mimetype !== 'application/pdf') {
      return res.status(400).json({ error: 'Please upload a PDF file.' });
    }

    const searchWord = (req.body.searchWord || '').toLowerCase();  // Convert search word to lowercase

    const pdfFilePath = req.file.path;

    fs.readFile(pdfFilePath, (err, data) => {
      if (err) {
        return res.status(500).json({ error: 'Error while reading the PDF file.' });
      }

      PDFParser(data)
        .then(parsedData => {
          const pdfText = parsedData.text;

          const lines = pdfText.split('\n');
          let wordCount = 0;
          const searchResults = [];
          let currentPage = 1;
          let currentPageText = '';

          for (let i = 0; i < lines.length; i++) {
            const line = lines[i].trim();
            currentPageText += line + ' '; // Accumulate the lines for searching

            if (line.toLowerCase().includes(searchWord)) {
              searchResults.push({
                page: currentPage,
                lineNumber: i + 1,
                line: line,
              });
            }

            if (line === '---Page Break---') {
              const words = currentPageText.split(/\s+/); // Split the accumulated text into words
              const wordMatches = words.filter(word => word.toLowerCase() === searchWord);
              wordCount += wordMatches.length;

              currentPageText = ''; // Reset the accumulated text
              currentPage++;
            }
          }

          fs.unlinkSync(pdfFilePath);

          res.json({ wordCount, searchResults });
        })
        .catch(error => {
          console.error('Error while processing the PDF:', error);
          res.status(500).json({ error: 'Error while processing the PDF.' });
        });
    });
  });
});
