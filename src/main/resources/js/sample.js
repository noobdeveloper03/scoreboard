fetch("http://localhost:8080/beyblades", {
  method: "GET",
  headers: {
    "Content-Type": "application/json"
  }
})
  .then(res => res.text())
  .then(data => console.log("SUCCESS:", data))
  .catch(err => console.error("ERROR:", err));
