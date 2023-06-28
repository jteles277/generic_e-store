import React from "react";
import { useNavigate } from "react-router-dom";

function LogoutButton() {
  const navigate = useNavigate();

  const handleLogout = () => {
    // Clear session storage
    sessionStorage.clear();

    // Redirect to the root path
    navigate("/");
  };

  return (
    <button onClick={handleLogout}>Logout</button>
  );
}

export default LogoutButton;