const API_BASE_URL = '/api/v1';

export const createAccount = async (accountData) => {
  const response = await fetch(`${API_BASE_URL}/account`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(accountData),
  });

  const data = await response.json();
  return { data, status: response.status };
};

export const getCustomer = async (customerNumber) => {
  const response = await fetch(`${API_BASE_URL}/account/${customerNumber}`, {
    method: 'GET',
  });

  const data = await response.json();
  return { data, status: response.status };
};
