<template>
    <div class="footer">
      <div class="footer-right">
        <span>BigTinyBrand, LLC</span>
      </div>
       
    </div>
  </template>
  
  <script>
  export default {
    name: 'FooterComponent',
    // Any other component logic can go here
  }
  </script>
  
  <style scoped>
  .footer {
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
    position:fixed;
    bottom:0;
    left:0;
    justify-content: space-between;
    align-items: left;
    color:whitesmoke;
    padding: 20px 50px;
    width: 100vw;
    height: 25px;
    z-index: 100;
    background-color: black;
  }
  
  .footer a {
    margin: 0 15px;
    color: white;
    text-decoration: none;
    transition: 0.3s;
  }
  
  .footer a:hover {
    text-decoration: underline;
  }
  </style>
  