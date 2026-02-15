#!/bin/bash

# Create simple colored PNG icons for different densities
# These are placeholder icons - you'll want to replace with actual Sadscans logo

# Install ImageMagick if needed
if ! command -v convert &> /dev/null; then
    echo "ImageMagick not installed, using basic method..."
fi

# Colors for Sadscans (you can customize)
ICON_DIR="src/tr/sadscans/src/main/res"

# Create placeholder icons with text "S"
for size in "48:mdpi" "72:hdpi" "96:xhdpi" "144:xxhdpi" "192:xxxhdpi"; do
    IFS=':' read -r dimension density <<< "$size"
    
    # Create a simple SVG and convert to PNG
    cat > /tmp/icon_$density.svg << SVGEOF
<svg width="$dimension" height="$dimension" xmlns="http://www.w3.org/2000/svg">
  <rect width="$dimension" height="$dimension" fill="#1a1a2e" rx="8"/>
  <text x="50%" y="50%" font-family="Arial" font-size="$((dimension * 60 / 100))" 
        fill="#16c79a" text-anchor="middle" dominant-baseline="central" font-weight="bold">S</text>
</svg>
SVGEOF

    # Try to convert with ImageMagick, fallback to inkscape
    if command -v convert &> /dev/null; then
        convert /tmp/icon_$density.svg $ICON_DIR/mipmap-$density/ic_launcher.png
        echo "Created $density icon ($dimension x $dimension)"
    elif command -v inkscape &> /dev/null; then
        inkscape /tmp/icon_$density.svg --export-filename=$ICON_DIR/mipmap-$density/ic_launcher.png -w $dimension -h $dimension
        echo "Created $density icon ($dimension x $dimension)"
    else
        echo "Warning: Neither ImageMagick nor Inkscape found. Icons not created for $density"
    fi
done

echo "Icon creation complete!"
