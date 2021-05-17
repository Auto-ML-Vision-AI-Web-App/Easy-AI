import logo from './logo.svg';
import './App.css';
import axios from 'axios';


function api_test () {
  const request = 'http://localhost:8080/'
  axios.get(request)
        .then(function(response) {
            console.log(response.data)
            console.log("성공");
        })
        .catch(function(error) {
            console.log("실패");
        })
};

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Eavy AI App
        </p>
        <button onClick={api_test}>API Test</button>
      </header>
    </div>
  );
}

export default App;
