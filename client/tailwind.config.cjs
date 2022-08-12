/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  darkMode: 'class',
  theme: {
    extend: {
      spacing: {
        '0.3': '0.1rem',
        '15': '3.75rem',
        '18': '4.5rem',
        '19': '4.75rem',
        '21': '5.25rem',
        '98': '24.5rem',
        '100': '25rem',
        '128': '32rem',
        '160': '40rem',
        '192': '48rem',
        '256': '64rem',
        '288': '72rem',
      },
      maxWidth: {
        '8xl': '86rem',
      },
    },
  },
  plugins: [],
}
