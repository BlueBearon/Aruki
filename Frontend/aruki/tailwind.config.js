/** @type {import('tailwindcss').Config} */
// filepath: tailwind.config.js
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      fontFamily: {
        code: ['Cascadia Code', 'monospace'],
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
    require('@tailwindcss/typography'),
  ],
}