import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import CreateAccount from './components/CreateAccount';
import SearchCustomer from './components/SearchCustomer';

function App() {
  return (
    <Router>
      <div className="min-h-screen bg-gray-100">
        <Navbar />
        <main className="py-8 px-4 sm:px-6 lg:px-8">
          <Routes>
            <Route path="/" element={<CreateAccount />} />
            <Route path="/search" element={<SearchCustomer />} />
          </Routes>
        </main>
        <footer className="bg-white border-t mt-auto">
          <div className="max-w-7xl mx-auto py-4 px-4 sm:px-6 lg:px-8">
            <p className="text-center text-gray-500 text-sm">
              Account Service - Java Microservice Exam by Anthony Castor
            </p>
          </div>
        </footer>
      </div>
    </Router>
  );
}

export default App;
