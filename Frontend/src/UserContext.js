import { createContext, useState } from 'react';

const UserContext = createContext();

export const UserProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  const updateUser = (userData) => {
    setUser(userData);
  };
  const getUserEmail = () => {
    return user ? user.email : 'Mister Guest';
  };

  const getId = () => {
    return user ? user.id : 'Mister Guest';
    };

    
  return (
    <UserContext.Provider value={{ user, updateUser, getUserEmail , getId  }}>
      {children}
    </UserContext.Provider>
  );
};

export default UserContext;