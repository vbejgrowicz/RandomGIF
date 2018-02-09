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
        let newStorage = JSON.parse(currentStorage);
        newStorage.push(input);
        localStorage.setItem('gifsearch', JSON.stringify(newStorage));
      } else {
        localStorage.setItem('gifsearch', JSON.stringify([input]))
      }
  }
}

window.addEventListener("load", loaded)
