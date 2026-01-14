import { useState } from 'react';
import { getCustomer } from '../services/api';

function SearchCustomer() {
  const [customerNumber, setCustomerNumber] = useState('');
  const [loading, setLoading] = useState(false);
  const [customer, setCustomer] = useState(null);
  const [error, setError] = useState(null);

  const handleSearch = async (e) => {
    e.preventDefault();
    if (!customerNumber.trim()) return;

    setLoading(true);
    setCustomer(null);
    setError(null);

    try {
      const { data, status } = await getCustomer(customerNumber);

      if (status === 302 || status === 200) {
        setCustomer(data);
      } else {
        setError(data.transactionStatusDescription || 'Customer not found');
      }
    } catch (err) {
      setError('Network error. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-4xl mx-auto">
      {/* Search Form */}
      <div className="bg-white shadow-xl rounded-lg overflow-hidden mb-6">
        <div className="bg-gradient-to-r from-blue-600 to-blue-700 px-6 py-4">
          <h2 className="text-2xl font-bold text-white">Search Customer</h2>
          <p className="text-blue-100 mt-1">Enter customer number to view account details</p>
        </div>

        <form onSubmit={handleSearch} className="p-6">
          <div className="flex flex-col sm:flex-row gap-4">
            <input
              type="text"
              value={customerNumber}
              onChange={(e) => setCustomerNumber(e.target.value)}
              placeholder="Enter Customer Number (e.g., 12345678)"
              className="flex-1 px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors text-lg"
            />
            <button
              type="submit"
              disabled={loading || !customerNumber.trim()}
              className="px-8 py-3 bg-blue-600 text-white rounded-lg font-medium hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-colors disabled:bg-blue-400 disabled:cursor-not-allowed"
            >
              {loading ? (
                <span className="flex items-center">
                  <svg className="animate-spin -ml-1 mr-2 h-5 w-5 text-white" fill="none" viewBox="0 0 24 24">
                    <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                    <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                  </svg>
                  Searching...
                </span>
              ) : (
                <span className="flex items-center">
                  <svg className="w-5 h-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                  </svg>
                  Search
                </span>
              )}
            </button>
          </div>
        </form>
      </div>

      {/* Error Message */}
      {error && (
        <div className="bg-red-50 border border-red-200 rounded-lg p-4 mb-6">
          <div className="flex items-center">
            <svg className="h-5 w-5 text-red-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
            <span className="ml-2 text-red-700">{error}</span>
          </div>
        </div>
      )}

      {/* Customer Details */}
      {customer && (
        <div className="bg-white shadow-xl rounded-lg overflow-hidden">
          <div className="bg-gradient-to-r from-green-500 to-green-600 px-6 py-4">
            <div className="flex items-center">
              <svg className="h-8 w-8 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
              <div className="ml-3">
                <h3 className="text-xl font-bold text-white">Customer Found</h3>
                <p className="text-green-100">Customer Number: {customer.customerNumber}</p>
              </div>
            </div>
          </div>

          <div className="p-6">
            {/* Customer Info */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
              <div className="space-y-4">
                <div>
                  <label className="text-sm font-medium text-gray-500">Customer Name</label>
                  <p className="text-lg font-semibold text-gray-900">{customer.customerName}</p>
                </div>
                <div>
                  <label className="text-sm font-medium text-gray-500">Email</label>
                  <p className="text-lg text-gray-900">{customer.customerEmail}</p>
                </div>
                <div>
                  <label className="text-sm font-medium text-gray-500">Mobile</label>
                  <p className="text-lg text-gray-900">{customer.customerMobile}</p>
                </div>
              </div>
              <div className="space-y-4">
                <div>
                  <label className="text-sm font-medium text-gray-500">Address Line 1</label>
                  <p className="text-lg text-gray-900">{customer.address1}</p>
                </div>
                {customer.address2 && (
                  <div>
                    <label className="text-sm font-medium text-gray-500">Address Line 2</label>
                    <p className="text-lg text-gray-900">{customer.address2}</p>
                  </div>
                )}
              </div>
            </div>

            {/* Accounts */}
            <div className="border-t pt-6">
              <h4 className="text-lg font-semibold text-gray-900 mb-4">Accounts</h4>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                {customer.savings && customer.savings.map((account, index) => (
                  <div key={index} className="bg-blue-50 border border-blue-200 rounded-lg p-4">
                    <div className="flex items-center justify-between mb-2">
                      <span className="text-sm font-medium text-blue-600">Savings Account</span>
                      <span className="bg-blue-100 text-blue-800 text-xs font-medium px-2 py-1 rounded">
                        #{account.accountNumber}
                      </span>
                    </div>
                    <p className="text-2xl font-bold text-gray-900">
                      ${account.availableBalance?.toFixed(2) || '0.00'}
                    </p>
                    <p className="text-sm text-gray-500">Available Balance</p>
                  </div>
                ))}

                {customer.checking && customer.checking.map((account, index) => (
                  <div key={index} className="bg-purple-50 border border-purple-200 rounded-lg p-4">
                    <div className="flex items-center justify-between mb-2">
                      <span className="text-sm font-medium text-purple-600">Checking Account</span>
                      <span className="bg-purple-100 text-purple-800 text-xs font-medium px-2 py-1 rounded">
                        #{account.accountNumber}
                      </span>
                    </div>
                    <p className="text-2xl font-bold text-gray-900">
                      ${account.availableBalance?.toFixed(2) || '0.00'}
                    </p>
                    <p className="text-sm text-gray-500">Available Balance</p>
                  </div>
                ))}

                {!customer.savings && !customer.checking && (
                  <p className="text-gray-500 col-span-2">No accounts found</p>
                )}
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default SearchCustomer;
