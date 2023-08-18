import { MutableRefObject, useEffect, useRef, useState } from 'react';

type LabelDropdownReturnType = [
  boolean,
  MutableRefObject<HTMLDivElement | null>,
  () => void,
];

export default function useDropdown(
  initialState: boolean
): LabelDropdownReturnType {
  const [isOpen, setIsOpen] = useState(initialState);
  const ref = useRef<HTMLDivElement | null>(null);

  const onSetOpenState = () => {
    setIsOpen((prev) => !prev);
  };

  useEffect(() => {
    const onClick = (e: MouseEvent) => {
      if (ref.current !== null && !ref.current.contains(e.target as Node)) {
        setIsOpen(false);
      }
    };

    if (isOpen) {
      window.addEventListener('click', onClick);
    }

    return () => {
      window.removeEventListener('click', onClick);
    };
  }, [isOpen]);

  return [isOpen, ref, onSetOpenState];
}
