# hine.org.uk

## Requirements
- A JDK
- libsass
- [Boot](http://boot-clj.com/)

## Develop
```bash
$ boot dev
```
Open [http://localhost:3000] in your browser.

Change the source at `/src/cljs/hine/org/uk/app.cljs` or the sass at `/sass/css/sass.scss`
and changes should be hot-reloaded into your browser.

## Deploy
```bash
$ boot production build target
$ git commit dist -m "deploying"
$ git subtree push --prefix dist origin gh-pages
```
