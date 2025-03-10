/** @type {import('tailwindcss').Config} */
module.exports = {
    content: [
      "./app/**/*.{js,jsx,ts,tsx}",
      "./pages/**/*.{js,jsx,ts,tsx}",
      "./components/**/*.{js,jsx,ts,tsx}",
    ],
    theme: {
      extend: {
        fontFamily: {
          sans: ["-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Oxygen", 
                 "Ubuntu", "Cantarell", "Fira Sans", "Droid Sans", "Helvetica Neue", "sans-serif"],
          mono: ["Cascadia Code", "Menlo", "Monaco", "Consolas", "Courier New", "monospace"],
        },
        colors: {
          background: "#ffffff",
          foreground: "#171717",
        },
        height: {
            '1/10': '10%',
            '9/10': '90%',
          },
          maxHeight: {
            '9/10': '90%',
            '1/10': '10%',
          },
      },
    },
    plugins: [
      require("@tailwindcss/typography"),
    ],
  };
  