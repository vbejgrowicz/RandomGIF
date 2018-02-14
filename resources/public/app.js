function loaded() {
  const form = document.getElementById("myform");
  if (form) {
    form.addEventListener("submit", function(e){
      saveToLocalStorage(e.target.input.value);
    });
  }
}

function saveToLocalStorage(input) {
  if (localStorage) {
      const currentStorage = localStorage.getItem('gifsearch');
      if (currentStorage) {
        let parsed = JSON.parse(currentStorage);
        if (!parsed.includes(input)) {
          parsed.unshift(input);
        }
        let newStorage = parsed.slice(0, 5);
        localStorage.setItem('gifsearch', JSON.stringify(newStorage));
      } else {
        localStorage.setItem('gifsearch', JSON.stringify([input]))
      }
  }
}

window.addEventListener("load", loaded)
