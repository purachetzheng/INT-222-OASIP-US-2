import { object, string, number, mixed} from 'yup'

const schema = object({
    notes: string().max(500).trim().label('Note'),
    datetime: object().shape({
        date: string().required().label('Date'),
        time: string().required().label('Time'),
    }),
    file: mixed().test('fileSize', 'The file size exceeds 10Mb.', (value) => {
        if (!value) return true
        return value.size <= FILE_SIZE
    }),
})
export default schema
