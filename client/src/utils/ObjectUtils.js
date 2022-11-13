export const objRenameKeys = (obj, changesMap) => {
    const arrayOfChangesMap = Object.entries(changesMap)
    for (const [key, value] of arrayOfChangesMap) {
        obj[value] = obj[key];
        delete obj[key];
      }

    return obj
}

export const objRemoveEmpty = (obj) => {
    return Object.entries(obj)
    .filter(([_, v]) => v != null)
    .reduce((acc, [k, v]) => ({ ...acc, [k]: v }), {});
}
